FROM openjdk:17-alpine

EXPOSE 5500

COPY build/libs/MoneyTransferService-1.0.0.jar money_trans.jar

CMD ["java", "-jar", "money_trans.jar"]