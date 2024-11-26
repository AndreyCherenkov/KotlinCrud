FROM ubuntu:latest
LABEL authors="a.cherenkov"

ENTRYPOINT ["top", "-b"]