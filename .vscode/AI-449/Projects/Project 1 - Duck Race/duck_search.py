#from duck_race_graph import duck_graph
from duck_queue import last_in_first_out
from duck_queue import first_in_first_out
import random
from duck import duck
class depth_search:
    duck_board = None
    search_queue = None
    def __init__(self, dg):
        self.duck_board = dg
        self.search_queue = last_in_first_out()
    def search(self):
        "Searches deeply for the goal node then moves the flag duck to it"
        if((self.duck_board.duck_deepness-1)*2 >= self.duck_board.duck_capacity):
           return "No solution"
        solution_str = ""
        arr = self.bogo_sort(self.duck_board.duck_list)
        goal_list = list()
        goal, flag_duck_index = self.search_to_goal()
        duck = self.duck_board.flag_duck
        start_pos = duck.position
        while(not duck.position.equals(goal)): #brute forces with the flag duck
            duck.move(duck.position.adjacent_nodes_x[len(duck.position.adjacent_nodes_x) - 1])
            solution_str += "R" + str(flag_duck_index) + " "
        duck.get_flag()
        while(not duck.position.equals(start_pos)): #runs after duck testing
            duck.move(duck.position.adjacent_nodes_x[0])
            solution_str += "L" + str(flag_duck_index) + " "
        return solution_str
    def bogo_sort(self, arr):
        new_arr = list()
        dupes = list()
        for i in range(0, len(arr)):
            num = random.randint(0, len(arr) - 1)
            while(num in dupes):
                num = random.randint(0, len(arr) - 1)
            dupes.append(num)
            new_arr.append(arr[dupes[i]])
        return new_arr
    
    def search_to_goal(self):
        start_pos = list()
        for i in self.duck_board.duck_list:
            start_pos.append(i.position)
        start = 0
        search_node = start_pos[start]
        self.search_queue.push(search_node)
        #goal_not_found = True
        while(True): #search until goal is found
            search_node = self.search_queue.pop()
            search_node = search_node.adjacent_nodes_x[len(search_node.adjacent_nodes_x) - 1]
            self.search_queue.push(search_node)
            if(search_node.goal):
                #goal_not_found = False
                break
            if(len(search_node.adjacent_nodes_x) == 1 and not search_node.equals(start_pos[start])):
                start += 1
                self.search_queue.pop()
                search_node = start_pos[start]
                self.search_queue.push(search_node)
        return search_node, start


    
class cost_search: #idea is to have least amount energy to be used in the moment
    duck_board = None
    flag_duck = None
    duck_num = None
    graph_len = None
    cost_queue = None
    duck_priorities = None
    start_poses = None
    def __init__(self, dg):
        self.duck_board = dg
        self.flag_duck = self.duck_board.duck_list[self.duck_board.flag_duck]
        self.duck_num = len(self.duck_board.duck_list)
        self.graph_len = self.duck_board.duck_deepness
        self.energy_max = self.duck_board.duck_capacity
        self.cost_queue = list() #ducks, will only move or transfer to a duck when needed via popping it
        for i in self.duck_board.duck_list:
             self.cost_queue.append(i)
        self.duck_priorities = list()
        for i in range(0, len(self.cost_queue)):
            self.duck_priorities.append(abs(self.cost_queue[i].position.node_num - self.flag_duck.position.node_num) // self.duck_board.duck_deepness)
        #priority based on y position in relation to the flag duck
        #self.start_poses = (list() for i.position in self.cost_queue)
        for i in range(0, len(self.duck_priorities)):
            self.cost_queue[i].priority = self.duck_priorities[i]
    
    def search(self): 
        """search() -> searches in a cost effective manner that only works if the duck capacity and duck amount - 1 is greater or equal to (the graph length - 1)*2"""
        if((self.graph_len - 1)*2 > self.energy_max + (self.duck_num - 1) or self.duck_num > self.energy_max):
            return "No solution"
        # solution examples
        # * = ducks
        # -> = movement
        # v = down transfer, ^ = upward transfer
        # * -> v
        # * -> * -> *F
        # -------------
        # * <- *
        # * <- * <- *
        # How the cost search system works for 2 ducks who have 3 energy capacity with graph length of 3, flag duck at 1.
        # works by (3-1)*2 = 3 + 1 -> 4 = 4
        # * -> * -> 2v
        # * -> * -> * -> 1v
        # * -> * -> * -> * -> * -> *F
        # * -> * -> * -> 2^
        # * -> * -> 2^
        # ----------------------------
        # * <- * <- *
        # * <- * <- * <- 1v
        # * <- * <- * <- * <- * <- *F
        # * <- * <- * <- *
        # * <- * <- *
        # other example with 5 ducks, 6 energy capacity, and 6 graph length with flag duck at 2
        # g(n) = duck priority - length
        solution_str = ""
        greatest_priority = 0
        pop_indices = list()
        for i in range(0, len(self.duck_priorities)): #greatest first
            if(greatest_priority < self.duck_priorities[i]):
                greatest_priority = self.duck_priorities[i]
                pop_indices = list()
            if(greatest_priority == self.duck_priorities[i]):
                pop_indices.append(i)
        moves = 0 
        greatestmoved = False
        transferred = False
        moves_while = greatest_priority
        for i in pop_indices:
                duck = self.cost_queue.pop(i)
                for i in range(0, greatest_priority):
                    duck.move(duck.position.adjacent_nodes_x[len(duck.position.adjacent_nodes_x) - 1])
                    index = str(duck.position.node_num // self.graph_len)
                    solution_str += "R" + index + " "
                self.cost_queue.insert(0, duck)
        priority_flake = greatest_priority - 1
        transfer_in_reserve = False
        while(not self.flag_duck.position.goal):
            if(priority_flake > 0):
                pop_indices = list()
                for i in range(0, len(self.cost_queue)):
                    if(priority_flake == self.cost_queue[i].priority):
                        pop_indices.append(i)
                for i in pop_indices:
                    duck = self.cost_queue.pop(i)
                    for i in range(0, moves_while):
                        duck.move(duck.position.adjacent_nodes_x[len(duck.position.adjacent_nodes_x) - 1])
                        index1 = str(duck.position.node_num // self.graph_len)
                        solution_str += "R" + index1 + " "
                    #try:
                    t_duck = None
                    for i in range (1, self.graph_len // (self.duck_num // 2)):
                            if(duck.position.node_num // self.graph_len - self.graph_len // 2 < 0):
                                transfer_duck = duck.position.adjacent_nodes_y[0].duck #top
                            else:
                                transfer_duck = duck.position.adjacent_nodes_y[len(duck.position.adjacent_nodes_y)- 1].duck #bottom
                            t_duck = transfer_duck
                            transfer_duck.transfer(duck.position)
                            index1 = str(duck.position.node_num // self.graph_len)
                            index2 = str(transfer_duck.position.node_num // self.graph_len)
                            solution_str += "T" + index2 + "->" + index1 + " "
                    duck.move(duck.position.adjacent_nodes_x[1])
                    index1 = str(duck.position.node_num // self.graph_len)
                    solution_str += "R" + index1 + " "
                    #except:
                    #    pass
                    self.cost_queue.insert(0, duck)
                priority_flake -= 1
                moves_while += 1
            elif(not transferred):
                pop_index = self.cost_queue.index(self.flag_duck)
                duck = self.cost_queue.pop(pop_index)
                for i in range(0, moves_while):
                        duck.move(duck.position.adjacent_nodes_x[len(duck.position.adjacent_nodes_x) - 1])
                        index1 = str(duck.position.node_num // self.graph_len)
                        solution_str += "R" + index1 + " "
                #check_priority = priority_flake + 1
                transfer_ducks = list()
                for i in duck.position.adjacent_nodes_y:
                    if(i.duck_occupied):
                        transfer_ducks.append(i.duck)
                u = 0
                for i in range (0, self.graph_len // (self.duck_num // 2)*len(transfer_ducks)): #amount that is transferred
                        if(len(transfer_ducks) == 2):
                            if(u == 0):
                                u = 1
                            elif(u == 1):
                                u = 0
                        previous_energy = transfer_ducks[u].energy 
                        transfer_ducks[u].transfer(duck.position)
                        index1 = str(duck.position.node_num // self.graph_len)
                        index2 = str(transfer_ducks[u].position.node_num // self.graph_len)
                        if(transfer_ducks[u].energy < previous_energy):    
                            solution_str += "T" + index2 + "->" + index1 + " "
                for t in transfer_ducks:
                    if(t.energy > moves_while):
                            transfer_in_reserve = True
                    #self.cost_queue.insert(0, transfer_ducks[u])
                self.cost_queue.insert(0, duck)
                transferred = True
            else:
                num = 0
                for num in range(0, len(self.cost_queue)):
                    if(self.cost_queue[num].isFlag):
                        break
                pop_index = num
                duck = self.cost_queue.pop(pop_index)
                duck.move(duck.position.adjacent_nodes_x[1])
                index1 = str(duck.position.node_num // self.graph_len)
                solution_str += "R" + index1 + " "
                self.cost_queue.insert(0, duck)
        self.flag_duck.get_flag()        
        ducks_not_at_home = True
        while(ducks_not_at_home):
            if(priority_flake > 0):
                pop_indices = list()
                for i in range(0, len(self.duck_priorities)):
                    if(priority_flake == self.duck_priorities[i]):
                        pop_indices.append(i)
                for i in pop_indices:
                    duck = self.cost_queue.pop(i)
                    while(len(duck.position.adjacent_nodes_x) > 1):
                        duck.move(duck.position.adjacent_nodes_x[0])
                        index1 = str(duck.position.node_num // self.graph_len)
                        solution_str += "L" + index1 + " "
                    self.cost_queue.insert(0, duck)
                if(priority_flake > greatest_priority):
                    priority_flake = 1
                else:    
                    priority_flake += 1
            else:
                num = 0
                for num in range(0, len(self.cost_queue)):
                    if(self.cost_queue[num].priority == 0):
                        break
                pop_index = num
                duck = self.cost_queue.pop(pop_index)
                for i in range(0, self.graph_len - moves_while - 1):
                    duck.move(duck.position.adjacent_nodes_x[0])
                    index1 = str(duck.position.node_num // self.graph_len)
                    solution_str += "L" + index1 + " "
                if(transfer_in_reserve):
                    transfer_ducks = list()
                    for i in duck.position.adjacent_nodes_y:
                        if(i.duck_occupied):
                            transfer_ducks.append(i.duck)
                    for u in transfer_ducks:
                        if(u.energy > moves_while):
                            u.transfer(duck.position)
                            index1 = str(duck.position.node_num // self.graph_len)
                            index2 = str(u.position.node_num // self.graph_len)
                            solution_str += "T" + index2 + "->" + index1 + " "
                while(len(duck.position.adjacent_nodes_x) > 1):
                    duck.move(duck.position.adjacent_nodes_x[0])
                    index1 = str(duck.position.node_num // self.graph_len)
                    solution_str += "L" + index1 + " "
                self.cost_queue.append(duck)
                priority_flake += 1
            ducks_not_at_home = False
            for i in self.duck_board.duck_list:
                if(len(i.position.adjacent_nodes_x) > 1):
                    ducks_not_at_home = True
        return solution_str
   
class heuristic_search: #idea is to have least amount of ducks and energy used to reach the goal
    duck_board = None
    heuristic_base = None
    heuristic_cost_move = None
    #g(n) =  |(energyTotal // duck_priority) - length)|
    # length = ((current position // deepness))
    # duck priority = ducks away from flag duck
    #  e.g. if flag duck's 3rd then moving from second or fourth duck
    # energyTotal is total energy expended from all moves known as heuristic cost overall
    #for h(n) = (deepness) - (moves) (starting)
    # moves being the amount of moves that were done already
    #h(n) changes based on flag duck's flag closeness and then other ducks' closeness to their starting positions
    #this heuristic is especially admissalbe because it position ducks 
    # based their current gas and their priority, making sure that flag duck gets the most fuel.
    goal_node = None
    def __init__(self, dg):
        self.duck_board = dg
        self.heuristic_base = dg.duck_deepness
        self.flag_duck = self.duck_board.duck_list[self.duck_board.flag_duck]
        self.goal_node = self.search_to_goal()
        self.cost_queue = list() #ducks, will only move or transfer to a duck when needed via popping it
        for i in self.duck_board.duck_list:
             self.cost_queue.append(i)
        self.duck_priorities = list()
        for i in range(0, len(self.cost_queue)):
            self.duck_priorities.append(abs(self.cost_queue[i].position.node_num - self.flag_duck.position.node_num) // self.duck_board.duck_deepness) 
            #priority based on y position in relation to the flag duck  
        for i in range(0, len(self.duck_priorities)):
            self.cost_queue[i].priority = self.duck_priorities[i]
    def search_to_goal(self):
        """"search_to_goal() -> searches for the goal via a breath-first search"""
        start_poses = list()
        goal_find_queue = first_in_first_out()
        for i in self.duck_board.duck_list:
            start_poses.append(i.position)
        for i in start_poses:
            goal_find_queue.push(i)
        node = goal_find_queue.pop()
        while(node.goal):
            try:
                node = node.adjacent_nodes_x[len(node.adjacent_nodes_x) - 1]
                goal_find_queue.push(node)
            except:
                goal_find_queue.pop()
        return node
    def search(self):
        """search() -> searches with a heuristic in mind, returns "No Solution in an edge case and when the base/duck capacity is above 1."""
        if(self.heuristic_base/self.duck_board.duck_capacity > 1): #5432 ban
            return "No solution"
        length = self.heuristic_base - 1
        duck_priority = 1
        energy_total = self.duck_board.duck_capacity
        goal_cost = abs((energy_total // duck_priority) - length)
        self.heuristic_cost_move = self.heuristic_base - 1
        total_cost = goal_cost + self.heuristic_cost_move
        needed_priority = ((self.heuristic_cost_move*2) // energy_total)
        solution_str = ""
        self.flag_duck.move(self.flag_duck.position.adjacent_nodes_x[0])
        solution_str = "R" + str(self.flag_duck.position.node_num // self.heuristic_base) + " "
        while(not self.flag_duck.position.goal): 
            goal_cost = 0
            duck = self.cost_queue.pop(0)
            energy_total = duck.energy
            duck_priority = duck.priority
            if(duck_priority > 0):
                total_cost = self.heuristic_cost_move*2
            if(duck_priority > needed_priority):
                continue
            times_moved = duck.moved_times
            if (duck_priority > 0):
                self.heuristic_cost_move = abs(energy_total - duck_priority) #h(n) change for higher duck priorities
            else:
                self.heuristic_cost_move = self.heuristic_base - 1 
            if(duck_priority > 0):
                length = (self.heuristic_base - 1) - duck_priority*2
                if(energy_total > times_moved + duck_priority):
                    goal_cost = abs((energy_total // duck_priority) - length)
                else: 
                    goal_cost = 999999 #failsafe error thing
            else:
                length = self.heuristic_base - 1
                if(energy_total > times_moved):
                    goal_cost = length - times_moved
            t_cost = goal_cost + self.heuristic_cost_move
            if(t_cost < total_cost):
                    #if(not (times_moved > 0 and duck.position.adjacent_nodes_y[len(duck.position.adjacent_nodes_y) - 1].duck_occupied)):
                        duck.move(duck.position.adjacent_nodes_x[len(duck.position.adjacent_nodes_x) - 1])
                        index1 = str(duck.position.node_num // self.heuristic_base)
                        solution_str += "R" + index1 + " "
                        duck.moved_times += 1
            if(times_moved < duck.energy and duck_priority > 0):
                    distance_divisor = duck.position.node_num // self.heuristic_base - self.flag_duck.position.node_num // self.heuristic_base
                    if(distance_divisor > 2):
                        store = duck.energy 
                        duck.transfer(duck.position.adjacent_nodes_y[len(duck.position.adjacent_nodes_y) - 1])
                        index1 = str(duck.position.node_num // self.heuristic_base)
                        index2 = str(duck.position.adjacent_nodes_y[len(duck.position.adjacent_nodes_y) - 1].node_num // self.heuristic_base)
                        if(store > duck.energy):
                            solution_str += "T" + index1 + "->" + index2 + " "
                    else:  
                        store = duck.energy   
                        duck.transfer(duck.position.adjacent_nodes_y[0])
                        index1 = str(duck.position.node_num // self.heuristic_base)
                        index2 = str(duck.position.adjacent_nodes_y[0].node_num // self.heuristic_base)
                        if(store > duck.energy):
                            solution_str += "T" + index1 + "->" + index2 + " "
            total_cost = t_cost
            self.cost_queue.append(duck)
        ducks_not_at_home = True
        self.flag_duck.moved_times += 1
        print(solution_str)
        self.heuristic_cost_move = self.heuristic_base - 1
        saved_index = None
        if(self.flag_duck.energy < self.flag_duck.moved_times):
            denom = self.flag_duck.moved_times - self.flag_duck.energy
            #saving_ducks = list()
            saving_duck = None
            for i in self.duck_board.duck_list:
                if(i.priority == 1 and i.energy >= denom*2 and len(i.position.adjacent_nodes_x) < 2):
                    saving_duck = i
            saved_index = self.duck_board.duck_list.index(saving_duck)
            if(saving_duck is None):
                return "No solution"
            if(denom > 2):
                denom /= 2
            for i in range(0, denom):
                saving_duck.move(saving_duck.position.adjacent_nodes_x[len(saving_duck.position.adjacent_nodes_x) - 1])
                saving_duck.moved_times += 1

        while(ducks_not_at_home):
            duck = self.cost_queue.pop(0)
            energy_total = duck.energy
            length = duck.moved_times
            if(length == 0 or duck.position.node_num // self.heuristic_base == saved_index):
                self.cost_queue.append(duck)
                continue
            if(duck_priority > 0):
                goal_cost = abs((energy_total // duck_priority) - length)
            else:
                goal_cost = abs(energy_total - length)
            self.heuristic_cost_move = length
            total_cost = goal_cost + self.heuristic_cost_move
            if(energy_total >= goal_cost):
                duck.move(duck.position.adjacent_nodes_x[0])
                index1 = str(duck.position.node_num // self.heuristic_base)
                solution_str += "L" + index1 + " "
                duck.moved_times -= 1
            elif(duck.isFlag):
                duck_reap = duck.position.adjacent_nodes_y[0].duck
                if(duck_reap is None):
                    duck_reap = duck.position.adjacent_nodes_y[len(duck.position.adjacent_nodes_y) - 1].duck
                for i in range(0, duck.moved_times):
                    duck_reap.transfer(duck.position)
                    index1 = str(duck.position.node_num // self.heuristic_base)
                    index2 = str(duck_reap.position.node_num // self.heuristic_base)
                    solution_str += "T" + index2 + "->" + index1 + " "
                #duck.moved_times += duck_reap.moved_times
                while(len(duck_reap.position.adjacent_nodes_x) > 1):
                    duck_reap.move(duck_reap.position.adjacent_nodes_x[0])
                    solution_str += "L" + index2 + " "
                while(len(duck.position.adjacent_nodes_x) > 1):
                    duck.move(duck.position.adjacent_nodes_x[0])
                    solution_str += "L" + index1 + " "
            self.cost_queue.append(duck)
            ducks_not_at_home = False
            for i in self.cost_queue:
                if(len(i.position.adjacent_nodes_x) > 1):
                    ducks_not_at_home = True
        return solution_str