#!/bin/bash
curl 'http://localhost:8080/movie' -H 'Content-Type: application/x-www-form-urlencoded' --data 'name=Mr.%20Grumpmeyer&genre=Comedy&yearReleased=1998&rating=PG'
