# Service for receiving/sending messages

## How to use

1. Installation and launch (All commands are executed from the project directory)
    1. Go to the project directory
    2. Execute the command `docker-compose -p citus up --scale worker={worker_num} -d --build` <br> where **worker_num** is the desired number of workers
