# docker compose
go to /src/deploy/docker and do "./stack up" or "./stack up -native"

# run jvm multi image
docker run --pull always --name jobrunr-example --rm -p8001:8001 goafabric/jobrunr-example:$(grep '^version=' gradle.properties | cut -d'=' -f2)

# run native image
docker run --pull always --name jobrunr-example-native --rm -p8001:8001 goafabric/jobrunr-example-native:$(grep '^version=' gradle.properties | cut -d'=' -f2) -Xmx32m

# run native image arm
docker run --pull always --name jobrunr-example-native --rm -p8001:8001 goafabric/jobrunr-example-native-arm64v8:$(grep '^version=' gradle.properties | cut -d'=' -f2) -Xmx32m
