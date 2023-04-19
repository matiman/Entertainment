Instructions on how to run the application:

- Clone the above project from dev branch to your local machine
- Make sure you have Java 17 installed on the machine you are running
- Assuming that you have IDEA, import the project as Gradle project
- Make sure you have mysql database running on your machine (this could be on a docker instance).
- If you have mysql installed on docker you can use the following command:
- #docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=mysecretpassword -e MYSQL_DATABASE=mydb mysql:latest
- See application.yml file if you want to change setting before running
- I used auto: update so the database should be created automatically for the first time.
- Then it appends afterwards when you restart your application.
- Build the project with gradle on Intelij and run the application
- http://localhost:8080/api/ is the gateway.
- Use the following URLs and parameters ( You can use curl or POSTMAN). Please change the body accordingly.
    - GET
        - actor:
            - curl -X GET http://localhost:8080/api/actors
            - curl -X GET http://localhost:8080/api/actors/1
        - movie:
            - curl -X GET http://localhost:8080/api/movies
            - curl -X GET http://localhost:8080/api/movies/1
    - Create
        - actor:
            - curl --header "Content-Type: application/json" --request POST --data '{"name":"Matty Abraham"}' http://localhost:8080/api/actors
        - movie
            - curl -X POST http://localhost:8080/api/movies -H 'Content-Type: application/json' -d '{"title": "Gorzilla 5","releaseDate": "1972-03-24","actors": [{"id": 3}]}'

    - Delete
        - actor
            - curl -X DELETE http://localhost:8080/api/actors/3
        - movie
            - curl -X DELETE http://localhost:8080/api/movies/2
    - Update
        - actor
            - curl -X PUT http://localhost:8080/api/actors/3 -H 'Content-Type: application/json' -d '{"name": "Mark Bill"}'

        - movie
            -  curl -X PUT http://localhost:8080/api/movies/1 -H 'Content-Type: application/json' -d '{"title": "The Godfather Part II","releaseDate": "1974-12-20","actors": [{"id": 3},{"id": 7}]}'



Please let me know if you encounter any issues.

 