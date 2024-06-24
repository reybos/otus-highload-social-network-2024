<h1> Нагрузочное тестирование </h1>

<h2> Исходные данные </h2>

- База данных заполнена тестовыми данными. Загружено 1_000_000 анкет
- Тестируется ручка `/user/search?first_name=&second_name=`

<h2> Сценарий </h2>

- Тестируем с помощью JMeter
- Регистрируем пользователя и получаем токен для него заранее
- Делаем запросы в ручку `/user/search?first_name=&second_name=`, в качестве параметров `first_name` и `second_name` передаем две рандомные буквы из списка `АБВГДЕЖЗИКЛМНОПРСТУШХФЭЯЧ`
- Делаем несколько тестов с различным количеством одновременных запросов
- Длительность каждого теста 1 минута
- Тест план лежит в папке `sn-data-loader/jmeter`

<h2> Результаты до оптимизации </h2>

- Запрос для поиска анкет по префиксу имени и фамилии (одновременно)
```sql
SELECT * 
FROM users 
WHERE first_name ILIKE ':firstName%' AND second_name ILIKE ':secondName%'
ORDER BY user_id;
```
- План запроса без индекса
<p>
  <img src="media/explain_before.png"  height="300" title="explain analyze before">
</p>

<h3 align="center"> 10 rps </h3>

<p align="center">
  <b>Latency</b><br>
  <img src="media/10_lat.png"  height="400">
</p>
<p align="center">
  <b>Throughput</b><br>
  <img src="media/10_thr.png"  height="400">
</p>

<h3 align="center"> 25 rps </h3>

<p align="center">
  <b>Latency</b><br>
  <img src="media/25_lat.png"  height="400">
</p>
<p align="center">
  <b>Throughput</b><br>
  <img src="media/25_thr.png"  height="400">
</p>

<h3 align="center"> 50 rps </h3>

<p align="center">
  <b>Latency</b><br>
  <img src="media/50_lat.png"  height="400">
</p>
<p align="center">
  <b>Throughput</b><br>
  <img src="media/50_thr.png"  height="400">
</p>

<h2> Возможные оптимизации </h2>
Для ускорения работы ручки предлагается сделать несколько вещей

1. Нужен индекс по полям first_name и second_name. Так как поиск по префиксу, то будет работать B-Tree индекс, добавляем составной индекс на оба этих поля.
2. Так как в запросе нет лимита, то стоит подумать над индексом для ускорения сортировки по user_id, так как выбираемых данных много. Индекс будет B-Tree, так как он хорошо работает при сортировке. Для обоснования этого индекса проведено тестирование с ним и без него при 50 rps

<h2> Результаты после оптимизации </h2>

- Запрос на построение индекса 
```sql
CREATE INDEX IF NOT EXISTS idx_first_name_second_name ON users (first_name, second_name);
CREATE INDEX IF NOT EXISTS idx_user_id ON users (user_id);
```

- План запроса с индексами
<p>
  <img src="media/explain_after.png"  height="275">
</p>

<h3 align="center"> 10 rps </h3>

<p align="center">
  <b>Latency</b><br>
  <img src="media/10_lat_idx.png"  height="400">
</p>
<p align="center">
  <b>Throughput</b><br>
  <img src="media/10_thr_idx.png"  height="400">
</p>

<h3 align="center"> 50 rps с idx_user_id </h3>

<p align="center">
  <b>Latency</b><br>
  <img src="media/50_lat_idx.png"  height="400">
</p>
<p align="center">
  <b>Throughput</b><br>
  <img src="media/50_thr_idx.png"  height="400">
</p>

<h3 align="center"> 50 rps без idx_user_id </h3>

<p align="center">
  <b>Latency</b><br>
  <img src="media/50_lat_idx_without.png"  height="400">
</p>
<p align="center">
  <b>Throughput</b><br>
  <img src="media/50_thr_idx_without.png"  height="400">
</p>

<h3 align="center"> 100 rps </h3>

<p align="center">
  <b>Latency</b><br>
  <img src="media/100_lat_idx.png"  height="400">
</p>
<p align="center">
  <b>Throughput</b><br>
  <img src="media/100_thr_idx.png"  height="400">
</p>

<h2> Выводы </h2>

**Предложенные оптимизации позволят:**

- улучшить latency запросов при различных rps
- держать большую нагрузку, до добавления индексов приложение падало при 50rps, теперь оно способно держать 100 rps (правда с неадекватным latency)