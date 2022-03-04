from typing import Type
from utils.Console import Console
from utils.Client import Client
from utils.DataBuilder import DataBuilder

def open_account(
    console: Type[Console],
    client: Type[Client]
    ) -> None:
    
    service_id = 1
    message_id = client.get_message_id()
    name = console.prompt_string('Enter your name : ')
    password = console.prompt_int('Enter your 4 digit pin number : ')
    while len(str(password)) != 4:
        password = console.prompt_int('Enter your 4 digit pin number : ')
    currency = console.prompt_string('Enter your currency : ')
    balance = console.prompt_float('Enter your initial balance : ')

    # Construct byte array
    builder = DataBuilder()
    builder.set_int('service_id',service_id).\
        set_int('message_id',message_id).\
        set_string('name',name).\
        set_int('password',password).\
        set_string('currency',currency).\
        set_float('balance',balance)

    print(builder.buffer)

    # Send data
    client.send(b'Sample mock data')

    # Receive response
    response = client.receive()
    print(response)
    

