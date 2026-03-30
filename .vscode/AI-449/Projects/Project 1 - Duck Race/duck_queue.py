class last_in_first_out:
    queue = None
    def __init__(self):
        self.queue = list()
    
    def push(self, node): 
        """push() -> pushes a node into the queue"""
        self.queue.append(node)
    def pop(self):
        """pop() -> pops a node from the queue and returns it"""
        store = self.queue[0]
        self.queue.remove(self.queue[0])
        return store
    
class first_in_first_out:
    def __init__(self):
        self.queue = list()
    
    def push(self, node): 
        """push() -> pushes a node into the queue"""
        if(len(self.queue) < 1):
            self.queue.append(node)
        else:
            self.queue.insert(0, node)
    def pop(self):
        """pop() -> pops a node from the queue and returns it"""
        store = self.queue[0]
        self.queue.remove(self.queue[0])
        return store