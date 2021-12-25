from com.zpmc.ztos.infra.business.base import HelloService

class HelloServicePython(HelloService):
    def __init__(self):
        self.value="Hello from python"

    def getHello(self):
        return self.value