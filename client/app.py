import textwrap
import sys
from utils.Console import Console
from utils.Client import Client
from services.open_account_service import open_account
from services.close_account_service import close_account
from services.update_account_service import update_account
from services.monitor_accounts_service import monitor_account

def main():

    # Client Configuration
    port = 2222
    host = 'localhost'
    # port = console.prompt_int("Enter client's port number : ")
    # host = console.prompt_string('Enter client address : ')
    client = Client(port,host)
  

    menu = '''
    -------------- Welcome to CZ4013 Distributed Banking System ---------------------

    Please select an option:
    1. Open a new account
    2. Close an existing account
    3. Withdraw from account / Deposit into account
    4. Monitor updates made to all bank account
    5. Idempotent operation (TO DO)
    6. Non-Idempotent operation (TO DO)
    7. Exit
    '''
    print(textwrap.dedent(menu))
  
    console = Console()

    while True:
        choice = console.prompt_int('Enter a choice: ', minimum = 1, maximum = 7)

        if choice == 1:
            open_account(console,client)
        elif choice == 2:
            close_account(console,client)
        elif choice == 3:
            update_account(console,client)
        elif choice == 4:
            monitor_account(console,client)
        elif choice == 5:
            pass
        elif choice == 6:
            pass
        elif choice == 7:
            sys.exit(0)
        
        print()

if __name__ == "__main__":
    main()
