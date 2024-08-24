# Main backend

## Technologies

## How to use

1. Installation and launch (All commands are executed from the project directory)
    1. Go to the project directory
    2. Execute the command `docker compose up -d --build`
    3. Checking the API:
        1. Import a Postman collection `./postman/social_network.postman_collection.json`
        2. Run a query with the name `registerUser`. Result:
       ```json 
       {
         "user_id": "l0s2ELyatOg0EDKYq0d0YdDTGBBuIX"
       }
       ```
       
        3. Run a query with the name `login`, substituting the user_id from the last step. Result:
       ```json
       {
         "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwOTYxNWY2YS00OGU1LTRiOWUtODFhMS03ZDU3M2VhNGM..."
       }
       ```
       
        5. Execute a request named `getUser`. Add the token from the previous request to the Authorization header, and the user_id from the first request to the URL. Result:
       ```json
       {
          "user_id": "l0s2ELyatOg0EDKYq0d0YdDTGBBuIX",
          "first_name": "Andrew",
          "second_name": "Bosyi",
          "birthdate": "2024-06-07",
          "biography": "books",
          "city": "Belgrade"
        }
       ```