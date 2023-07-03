FROM openjdk:17-alpine

EXPOSE 5500

COPY build/libs/MoneyTransferService-0.0.1.jar money_trans.jar

CMD ["java", "-jar", "money_trans.jar"]