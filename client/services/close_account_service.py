from typing import Type
from utils.Console import Console
from utils.Client import Client

def close_account(
    console: Type[Console],
    client: Type[Client]
    ) -> None:
    
    service_id = 2
    message_id = client.get_message_id()
    name = console.prompt_string('Enter your name : ')
    acc_no = console.prompt_int('Enter your account number : ')
    password = console.prompt_int('Enter your 6 digit pin number : ')
    while len(str(password)) != 6:
        password = console.prompt_int('Enter your 6 digit pin number : ')

    # Send data
    client.send(b'Sample mock data')

    # Receive response
    response = client.receive()
    print(response)

