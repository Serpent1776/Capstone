"""All possible statements:
\tA: I am innocent.
\tB: The killer has an odd number.
\tC: I am standing next to the killer.
\tD: The killer is telling the truth.
\tE: My number is smaller than the killer's number.
\tF: What that person said. (See below for more on this one.)
\tG: Both the first and last person are lying.
\tH: The people next to me are telling the truth."""
class Domain:
    """Domain makes domains of given a set of statements"""
    __statements = None
    __general_domain = None
    def __init__(self, statements, general_domain):
        self.__statements = statements
        self.__general_domain = general_domain