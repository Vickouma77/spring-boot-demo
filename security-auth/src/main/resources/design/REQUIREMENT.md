```gherkin
Feature: Account Deactivation
  
   scenario: Landlord Deactivation
        Given Landlord initiates deactivation
        When Gateway Service verifies identity
        Then Authorization confirmed
        And Onboarding Service processes deactivation
        And Retrieve associated portfolios
        And Remove all portfolio entries from Database
        And Notify managers of removal
        And Inform managers of access revocation
        And Close billing account
        And Billing account closed confirmation
        And Revoke user access confirmation
        And Deactivate landlord account in Database
        And Send deactivation confirmation to landlord 

   scenario: Manager Deactivation
        Given Manager initiates deactivation
        When Gateway Service verifies identity
        Then Authorization confirmed
        And Onboarding Service processes deactivation
        And Identify portfolios with manager access
        And Remove manager from portfolios in Database
        And Confirm manager removal in Portfolio Service
        And Notify landlords of manager removal
        And Inform landlords of access revocation
        And Revoke user access confirmation for manager
        And Delete manager account from Database
        And Send deactivation confirmation to manager
```