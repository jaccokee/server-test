#!/bin/bash
curl 'http://localhost:8080/movie' -H 'Content-Type: application/json' --data '{"name":"Three Godzillas","genre":"Drama","yearReleased":1995,"rating":"PG"}'
