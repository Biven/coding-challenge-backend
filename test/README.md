## How to build image and run service locally in docker
Run service in docker:
```
docker-compose -f docker-compose-local.yml up -d
```

## Testing API Curls:

Healthcheck:
```
curl --location --request GET 'http://localhost:8080/healthcheck'
```

Post application:
```
curl --location --request POST 'http://localhost:8080/applicant' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "biven.mail@gmail.com",
    "name": "Yauheni",
    "githubUser": "biven",
    "pastProjects": [
        {
            "projectName": "project1",
            "employment": "freelance",
            "capacity": "fullTime",
            "startYear": 2010,
            "role": "developer",
            "teamSize": 10,
            "duration": "P2M3D"
        },
        {
            "projectName": "project3",
            "employment": "freelance",
            "capacity": "partTime",
            "startYear": 2012,
            "role": "developer",
            "teamSize": 10,
            "duration": "P2M3D"
        }
    ]
}'
```

List applications:
```
curl --location --request GET 'http://localhost:8080/reviewer/applications'
```

Download pdf report:
```
curl --location --request GET 'http://localhost:8080/reviewer/applications/biven.mail@gmail.com'
```