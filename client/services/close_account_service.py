from typing import Type
from utils.Console import Console
from utils.Client import Client
from utils.DataBuilder import DataBuilder

def close_account(
    console: Type[Console],
    client: Type[Client]
    ) -> None:
    
    service_id = 2
    message_id = client.get_message_id()
    name = console.prompt_string('Enter your name : ')
    acc_no = console.prompt_int('Enter your account number : ')
    password = console.prompt_int('Enter your 4 digit pin number : ')
    while len(str(password)) != 4:
        password = console.prompt_int('Enter your 4 digit pin number : ')

    # Constuct byte array
    builder = DataBuilder()
    builder.set_two_byte('service_id',service_id).\
        set_int('message_id',message_id).\
        set_string('name',name).\
        set_int('acc_no',acc_no).\
        set_int('password',password)

    print(builder.buffer)
    print(builder.create())
    
    # Send data
    client.send(builder.create())

    # Receive response
    # response = client.receive()
    while True:
        try:
            response = client.receive()
            break
        except:
            print('Timeout ... attempting to resend again.')
            client.send(builder.create())
            
    print(response)

