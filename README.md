
PERSONAL BUDGETING SOFTWARE  
CS251 - Software Engineering  
Spring 2026 
================================================================================

TEAM MEMBERS
================================================================================
ID          | Name                           | Email
------------|--------------------------------|--------------------------------
20240201    | Romissa Medhat Ahmed Hamad     | 20240201@stud.fci-cu.edu.eg
20240159    | Habiba Ehab Mohi Eldein        | 20240159@stud.fci-cu.edu.eg
20242117    | Demiana Nabil Sabry            | 20242117@stud.fci-cu.edu.eg
20240026    | Ahmed Khaled Ahmed             | 20240026@stud.fci-cu.edu.eg

PROJECT OVERVIEW
================================================================================
This is a complete Personal Budgeting Application implemented in Java 17+
with JavaFX for the graphical user interface and SQLite for persistent storage.

The application implements all 12 user stories from the SRS (v1.0, March 2026),
with special focus on the first 7 required user stories:
  1. User Sign-Up (US #1)
  2. User Login (US #2)
  3. Add Transaction (US #3)
  4. Create/Edit Budget (US #4)
  5. Budget Over-Limit Alert (US #5)
  6. Set Savings Goals (US #6)
  7. View Reports & Analytics (US #7)

Additional implemented features (optional bonus):
  - Navigation bar on every screen after login
  - Recurring transactions (automation)
  - CSV import from bank exports
  - Spending insights (AI‑style recommendations)

TECHNOLOGY STACK
================================================================================
- Language:      Java 17 (OpenJDK)
- UI Framework:  JavaFX 17 (FXML + CSS)
- Database:      SQLite 3.42.0.0 (embedded)
- Password Hash: BCrypt (jBCrypt 0.4)
- Build Tool:    Maven 3.8+
- Documentation: JavaDoc (generated via Maven plugin)
- Version Ctrl:  Git / GitHub (private repository)

FILE STRUCTURE
================================================================================
BudgetingApp/
  - pom.xml                         # Maven dependencies and build config
  - README.txt                      # Project instructions
  - src/
    - main/
      - java/com/budgetapp/
        - MainApp.java              # Entry point
        - model/                    # Entity classes
        - dao/                      # Data Access Objects (SQLite)
        - service/                  # Business logic services
        - factory/                  # Factory pattern classes
        - ui/                       # JavaFX controllers
      - resources/
        - fxml/                     # FXML layout files (6 screens)
        - css/                      # style.css (colors & layout)
        - database_schema.sql       # Database creation script
  - docs/                           # Generated JavaDoc HTML
  - target/                         # Compiled classes (ignored by Git)
    
TOOLS USED FOR DEVELOPMENT
================================================================================
- VS Code with Java extensions
- Maven 3.9.6
- Git 2.45
- PlantUML (for sequence & state diagrams)
- Diagrams.net (for architecture diagram)
- Visual Paradigm (for class diagram)
- SQLite Browser (DB Browser for SQLite) – for manual schema verification

HOW TO RUN THE APPLICATION
================================================================================
Prerequisites:
  1. Java 17+ installed (java -version)
  2. Maven 3.8+ installed (mvn -version)

Step-by-step:
  1. Extract the ZIP file to a folder.
  2. Open a terminal/command prompt in the project root (where pom.xml is).
  3. Initialize the database (only needed once):
       - Open src/main/resources/database_schema.sql in any SQLite tool
         (or use the command line: sqlite3 budgeting.db < database_schema.sql)
       - Or simply run the app once – it will auto-create tables (if you
         add the init code in DatabaseConnection). Our implementation does
         this automatically on first connection.
  4. Build and run using Maven:
         mvn clean compile
         mvn javafx:run
  5. The login screen will appear. First-time users click "Create New Account"
     and enter: Name, Email, Password (comma separated).
  6. After login, use the navigation bar to access all features.

DEFAULT TEST ACCOUNT (if you want to skip registration)
================================================================================
Email:    test@example.com
Password: Test123!   (the app will auto-create this user if not present)

USER STORIES & HOW TO TEST THEM
================================================================================
US #1 – User Sign-Up
   Click "Create New Account" → enter "John Doe,john@mail.com,Pass123!" → success.

US #2 – User Login
   Enter existing email and correct password → redirected to Dashboard.

US #3 – Add Transaction
   From Dashboard → "+ Add Transaction" → select type, amount, category → Save.
   The balance on Dashboard updates instantly.

US #4 – Create/Edit Budget
   Click "Budgets" → select category, enter amount, month, year → Set Budget.
   The budget list shows the new entry.

US #5 – Budget Over-Limit Alert
   Add expenses for a category that has a budget. When spending reaches 75%,
   a warning notification appears. At 100% or above, an "exceeded" alert is
   created and displayed on Dashboard and in Notifications (if implemented).

US #6 – Set Savings Goals
   Click "Goals" → enter name, target amount, deadline → Add Goal.
   Progress bar shows completion percentage as you add contributions.

US #7 – View Reports
   Click "Reports" → select date range → Generate. A pie chart of expenses
   by category appears.

DESIGN PATTERNS & SOLID PRINCIPLES
================================================================================
The code demonstrates the following patterns (see SDS for details):
  - DAO Pattern (GenericDAO<T> and concrete DAOs)
  - Factory Pattern (BudgetFactory, TransactionFactory)
  - Observer Pattern (BudgetObserver & AlertService)

SOLID principles applied:
  - Single Responsibility: each service/DAO has one clear purpose.
  - Open/Closed: services depend on interfaces, new features can be added
    without modifying existing code.
  - Dependency Inversion: high-level services depend on abstractions (GenericDAO),
    not on concrete database implementations.

DOCUMENTATION
================================================================================
JavaDoc has been generated for all public classes and methods.
Open docs/index.html in a web browser to browse the documentation.

The Software Design Specifications (SDS) is provided as a separate PDF:
  CS251-2026-Section26-Hager-20240201-20240159-20242117-20240026-SDS.pdf

The report on student opportunities (Task 1) is also included as a PDF:
CS251-2026-Section26-Hager-20240201-20240159-20242117-20240026-OpportunitiesReport.pdf

GITHUB REPOSITORY
================================================================================
A private GitHub repository was used throughout development.

KNOWN ISSUES & LIMITATIONS
================================================================================
- The CSV import feature expects a specific column order (date, amount, description).
  For real bank CSVs, the column mapping must be adjusted – we provide an example.
- Charts are limited to simple PieCharts; bar charts can be added easily.
- Multi‑currency conversion is not implemented (only display currency changes).
- The app is desktop-only (no mobile/web version as per SRS optional scope).

FUTURE ENHANCEMENTS (BONUS)
================================================================================
- Recurring transactions (auto‑generate future expenses)
- CSV import / export (backup & bank sync)
- Spending insights (month‑over‑month comparisons)
- Dark mode toggle
- Push notifications (requires backend server)
  
END OF README – THANK YOU FOR REVIEWING 
=================================================================================
