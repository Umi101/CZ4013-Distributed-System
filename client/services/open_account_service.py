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
    while balance < 0:
        balance = console.prompt_float('Enter your initial balance : ')
    # Construct byte array
    builder = DataBuilder()
    builder.set_two_byte('service_id',service_id).\
        set_int('message_id',message_id).\
        set_string('name',name).\
        set_int('password',password).\
        set_string('currency',currency).\
        set_float('balance',balance)

    print(builder.buffer)
    print(builder.create())

    # Send data
    client.send(builder.create())

    # Receive response
    while True:
        try:
            response = client.receive()
            break
        except TimeoutError as e:
            print('Timeout ... attempting to resend again.')
            client.send(builder.create())


    print(response.decode('ascii'))
    

