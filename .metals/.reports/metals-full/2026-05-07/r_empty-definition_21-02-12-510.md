error id: file:///E:/Git/CS215-A2-20240201-20240159-20242117-20240026/src/main/java/com/budgetapp/factory/TransactionFactory.java:_empty_/Transaction#setCategoryId#
file:///E:/Git/CS215-A2-20240201-20240159-20242117-20240026/src/main/java/com/budgetapp/factory/TransactionFactory.java
empty definition using pc, found symbol in pc: _empty_/Transaction#setCategoryId#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 844
uri: file:///E:/Git/CS215-A2-20240201-20240159-20242117-20240026/src/main/java/com/budgetapp/factory/TransactionFactory.java
text:
```scala
package com.budgetapp.factory;

import com.budgetapp.model.Transaction;
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
        t.se@@tCategoryId(categoryId);
        t.setDescription(desc);
        t.setDate(LocalDateTime.now());
        return t;
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/Transaction#setCategoryId#