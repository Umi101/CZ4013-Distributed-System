from typing import Type
from lib.Console import Console
from lib.Client import Client

def monitor_account(
    console: Type[Console],
    client: Type[Client]
    ) -> None:
    
    service_id = 4
    message_id = client.get_message_id()
    interval = console.prompt_int('Enter time interval (MINS) : ')

    # Construct byte array

    # Send data
    client.send(b'Sample mock data')

    # Receive response
    response = client.receive()
    print(response)
    

