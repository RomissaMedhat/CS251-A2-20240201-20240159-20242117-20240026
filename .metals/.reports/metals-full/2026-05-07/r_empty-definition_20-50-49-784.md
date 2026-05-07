error id: file:///E:/Git/CS215-A2-20240201-20240159-20242117-20240026/src/main/java/com/budgetapp/factory/TransactionFactory.java:com/budgetapp/model/Transaction#
file:///E:/Git/CS215-A2-20240201-20240159-20242117-20240026/src/main/java/com/budgetapp/factory/TransactionFactory.java
empty definition using pc, found symbol in pc: com/budgetapp/model/Transaction#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 61
uri: file:///E:/Git/CS215-A2-20240201-20240159-20242117-20240026/src/main/java/com/budgetapp/factory/TransactionFactory.java
text:
```scala
package com.budgetapp.factory;

import com.budgetapp.model.@@Transaction;
import com.budgetapp.model.TransactionType;
import java.time.LocalDateTime;

public class TransactionFactory {
    public static Transaction createExpense(int userId, double amount, int categoryId, String desc) {
        Transaction t = new Transaction();
        t.setUserId(userId);
        t.setAmount(amount);
        t.setType(TransactionType.EXPENSE);
        t.setCategoryId(categoryId);
        t.setDescription(desc);
        t.setDate(LocalDateTime.now());
        return t;
    }

    public static Transaction createIncome(int userId, double amount, int categoryId, String desc) {
        Transaction t = new Transaction();
        t.setUserId(userId);
        t.setAmount(amount);
        t.setType(TransactionType.INCOME);
        t.setCategoryId(categoryId);
        t.setDescription(desc);
        t.setDate(LocalDateTime.now());
        return t;
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: com/budgetapp/model/Transaction#