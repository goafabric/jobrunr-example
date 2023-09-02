# docker compose
go to /src/deploy/docker and do "./stack up" or "./stack up -native"

# run jvm multi image
docker run --pull always --name jobrunr-example --rm -p8000:8000 goafabric/jobrunr-example:1.0.0-SNAPSHOT

# run native image
docker run --pull always --name jobrunr-example-native --rm -p8000:8000 goafabric/jobrunr-example-native:1.0.0-SNAPSHOT -Xmx32m

# run native image arm
docker run --pull always --name jobrunr-example-native --rm -p8000:8000 goafabric/jobrunr-example-native-arm64v8:1.0.0-SNAPSHOT -Xmx32m
