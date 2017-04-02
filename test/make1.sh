#!/bin/bash
curl -X POST 'http://localhost:8080/api' -H 'Content-Type: application/x-www-form-urlencoded' --data 'name=Clam%20Stew&genre=Thriller&yearReleased=2009&rating=PG-13'
