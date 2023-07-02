=======================
WordSquare 1.0
=======================

1. How to run:
Execute the below command:
java -jar WordSquare.jar StringToProcess GridSize ShuffleToggle

e.g
java -jar WordSquare-1.0-SNAPSHOT.jar "aaccdeeeemmnnnoo", 4, true

Known Issues:
- Jar file must be present in the root of the project and executed from there, instead from within the target folder.
- Due to the randomized nature of the algorithm, the build may not find a solution on its first attempt and may require
multiple runs to succeed.

