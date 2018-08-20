# DailyTradeReporting

This application will read Trade information from Trade.txt, 

Sample data:
MS/B/0.02/AED/05 Apr 2017/23 Apr 2017/200/100.22

And will apply below logic to calculate the Trade FX and Settlement date:
1) A work week starts Monday and ends Friday, unless the currency of the trade is AED or SAR, where the work week starts Sunday and ends Thursday. No other holidays to be taken into account.
2) A trade can only be settled on a working day.
3) If an instructed settlement date falls on a weekend, then the settlement date should be changed to the next working day.
4) USD amount of a trade = Price per unit * Units * Agreed Fx

And report will be generated thats shows:
1) Amount in USD settled incoming everyday
2) Amount in USD settled outgoing everyday
3) Ranking of entities based on incoming and outgoing amount. Eg: If entity foo instructs the highest amount for a buy instruction, then foo is rank 1 for outgoing


Glossary of terms:
1) Instruction: An instruction to buy or sell
2) Entity: A financial entity whose shares are to be bought or sold
3) Instruction Date: Date on which the instruction was sent to JP Morgan by various clients
4) Settlement Date: The date on which the client wished for the instruction to be settled with respect
to Instruction Date
5) Buy/Sell flag:
6) B – Buy – outgoing
7) S – Sell – incoming
8) Agreed Fx is the foreign exchange rate with respect to USD that was agreed
9) Units: Number of shares to be bought or sold

Main application to run:
TradeReportingMain.java

Trade file: Trade.txt

Report will be generated in: log/tradereporting.log


Standlone jar with all dependencies can be created using 
mvn clean compile assembly:single

Jar will be created in target folder.

Command to execute the JAR:
java -jar DailyTradeReporting-0.0.1-SNAPSHOT-jar-with-dependencies.jar

Log with report will be generated inside target/logs folder
Filename: tradereporting.log