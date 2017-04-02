#!/bin/bash
curl -X PUT 'http://localhost:8080/api/1' -H 'Content-Type: application/x-www-form-urlencoded' --data 'name=Clam%20Stew%202%20-%20Mollusks%20Gone%20Wild&genre=Drama&yearReleased=2011&rating=R'
