from item import Item
from epl import EPL
class Container:
    def __init__(self, id, width=1, depth=1, height=1, weight=0):
        self.__id = id
        self.__width = width
        self.__depth = depth
        self.__height = height
        self.__weight = weight
        self.__fv = width * depth * height
        self.__locations = dict()
        self.__eps = EPL()

    def get_height(self):
        return self.__height
    
    def get_base_area(self):
        return self.__depth * self.__width
    
    def can_fit_in(self, i):
        return bool(i.get_weight() <= self.__weight and
            i.get_width() <= self.__width and
            i.get_height() <= self.__height and
            i.get_depth() <= self.__depth)
    
    def get_possible_eps(self, i):
        return [ep for ep in self.__eps.get_possible_eps(i)]
    
    def get_best_ep(self, i, f):
        ans  = None
        min_val = float('inf')
        pts = self.get_possible_eps(i)
        for pt in pts:
            if f(pt, i) < min_val:
                min_val = f(pt, i)
                ans = pt
        return ans, min_val

    def get_id(self):
        return self.__id
    
    def add_item(self, i, pt=(0,0,0)):
        self.__locations[i] = pt
        self.__fv -= pt[0] * pt[1] * pt[2]
        self.__weight -= i.get_weight()
        self.__eps.update(i)
    

    

