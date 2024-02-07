FROM eclipse-temurin:21-jre-jammy
RUN ["mkdir", "app"]
WORKDIR app
COPY ./bootstrap/target/*-jar-with-dependencies.jar app.jar
EXPOSE 8080

# Set environment variables
ENV SCHEDULER_TASK_PERIOD_IN_MILLISECONDS=60000
ENV SCHEDULER_TASK_PAUSE_IN_MILLISECONDS=60000
ENV DATAPROVIDER_DOMAIN=https://pro-api.coinmarketcap.com
ENV DATAPROVIDER_PATH=/v1/cryptocurrency/listings/latest
ENV DATAPROVIDER_API_HEADER=X-CMC_PRO_API_KEY
ENV DATAPROVIDER_REQUEST_TIMEOUT_IN_MILLISECONDS=5000
ENV PERSISTENCE_DOMAIN=http://elasticsearch:9200
ENV PERSISTENCE_INDEX_NAME=crypto
ENV API_ADDRESS=0.0.0.0
ENV API_PORT=8080

# -Xms512m - sets the initial heap size to 512mb of the maximum heap size.
# -Xmx1024m - sets the maximum heap size to 1024mb of the container memory limit.
# -XX:InitialRAMPercentage=60 - sets the initial heap size to 60% of the maximum heap size.
# -XX:MaxRAMPercentage=60 - sets the maximum heap size to 60% of the container memory limit. This option is useful when you want to limit the heap size relative to the available memory, without specifying an explicit size.
# -XX:MaxMetaspaceSize=128m - sets the maximum size of the metadata space to 128Mb, and the JVM will allocate space as needed up to this limit.
# -server - tells the JVM to use the "server" version of the virtual machine, which is optimized for long-running, multi-threaded applications.
# -XX:+UnlockExperimentalVMOptions - option unlocks experimental options for the JVM. This is required to use certain options that are not yet fully supported.
# -XX:+UseCGroupMemoryLimitForHeap - option tells the JVM to use the memory limit set by Docker's cgroup for the container as the maximum heap size.
ENTRYPOINT ["java", "-jar", "app.jar", "env"]
