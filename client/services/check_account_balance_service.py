from typing import Type
from utils.Console import Console
from utils.Client import Client
from utils.DataBuilder import DataBuilder

def check_account_balance(
    console: Type[Console],
    client: Type[Client]
        ) -> None:
    
    service_id = 5
    message_id = client.get_message_id()
    name = console.prompt_string('Enter your name : ')
    acc_no = console.prompt_int('Enter your account number : ')
    password = console.prompt_int('Enter your 4 digit pin number : ')
    while len(str(password)) != 4:
        password = console.prompt_int('Enter your 4 digit pin number : ')
        
    builder = DataBuilder()
    builder.set_two_byte('service_id', service_id).\
        set_int('message_id',message_id).\
        set_string('name',name).\
        set_int('acc_no',acc_no).\
        set_int('password',password)
        
        
    # Send data
    client.send(builder.create())
    
    # Receive response
    response = client.receive()
    print(response)