from typing import Type
from utils.Console import Console
from utils.Client import Client
from utils.DataBuilder import DataBuilder

def monitor_account(
    console: Type[Console],
    client: Type[Client]
    ) -> None:
    
    service_id = 4
    message_id = client.get_message_id()
    interval = console.prompt_int('Enter time interval (MINS) : ')

    # Construct byte array
    builder = DataBuilder()
    builder.set_int('service_id',service_id).\
        set_int('message_id',message_id).\
        set_int('interval',interval)

    print(builder.buffer)


    # Send data
    client.send(b'Sample mock data')

    # Receive response
    response = client.receive()
    print(response)
    
