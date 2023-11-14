FROM ubuntu:latest
LABEL authors="dongwoo"

ENTRYPOINT ["top", "-b"]