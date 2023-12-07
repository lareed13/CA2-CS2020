A  README  file  explaining  what  you  have  implemented,  and  challenges  you  encountered 
(text or PDF)

What I have Implemented:
    Main Program
    - BudgetBase : Handles GUI logic and changes made to GUI
    - UndoActions : Handles Undo class creation, handles undo action.
    - ValueCalculation : Handles logic to calculate yearly income figures.
    Testing
    - BudgetBaseTest : Handles testing methods in BudgetBaseTest
    - UndoActionsTest : Handles the testing for UndoActionsTest

Challanges I encountered:
    - The layout was difficult to maintain as later changes to UI would cause me to readjust all existing values for row and column for each item if affected.
    - Readability was hard to maintain as Some of the methods became pretty extensive.
    - Challanges with class visibility within other components.
    - Undo Actions, the ability to undo previous actions caused me to rethink and adjust the main methods in my program.
    - Storage of current state for undo actions, using event listeneres sometimes was a bit confusing and would store states of which were unneccessary.
    - Testing, At the start was difficult to run my tests.
    - I forgot to add an error message but figured out a reliable method relatively quickly.
