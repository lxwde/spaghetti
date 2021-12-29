from com.zpmc.ztos.infra.business.base import HelloService
from com.zpmc.ztos.infra.business.account import User


class HelloServicePython(HelloService):
    def __init__(self):
        self.value="Hello from python"

    def getHello(self):
        User.create("pythonUser", "pythonUser")
        return self.value