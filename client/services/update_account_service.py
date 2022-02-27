from typing import Type
from utils.Console import Console
from utils.Client import Client

def update_account(
    console: Type[Console],
    client: Type[Client]
    ) -> None:
    
    service_id = 3
    message_id = client.get_message_id()
    next_choice = console.prompt_int('Enter 1 to withdraw / Enter 2 to deposit: ', minimum = 1, maximum = 2)
    name = console.prompt_string('Enter your name : ')
    password = console.prompt_int('Enter your 6 digit pin number : ')
    while len(str(password)) != 6:
        password = console.prompt_int('Enter your 6 digit pin number : ')
    amount = console.prompt_float('Enter your initial balance : ', minimum = 0.01, maximum = float('inf'))
    if next_choice == 1:
        amount = -amount
    
    # Construct byte array

    # Send data
    client.send(b'Sample mock data')

    # Receive response
    response = client.receive()
    print(response)
    

