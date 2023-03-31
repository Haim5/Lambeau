import math
from container import Container
from item import Item
class Packer:
    def __init__(self, items, width=1, depth=1, height=1, weight=0):
        self.__width = width
        self.__depth = depth
        self.__height = height
        self.__weight = weight
        self.__items = items
        self.__sol = None  

    def pack(self, sort_f, l, place_f):
        c1 = Container(width=self.__width, depth=self.__depth, height=self.__height, weight=self.__weight)
        ans = [c1]
        sorted_items = sort_f(self.__items, l, c1)
        for i in sorted_items:
            if self.can_fit_in(i):
                pts = None
                min_val = float('inf')
                for bin in ans:
                    pt, val = bin.get_best_ep(i=i, f=place_f)
                    if pt is not None and min_val > val:
                        min_val = val
                        pts = (bin, pt)
                if pts is None:
                    c = Container(width=self.__width, depth=self.__depth, height=self.__height, weight=self.__weight)
                    c.add_item(i=i)
                    ans.append(c)
                else:
                    pts[0].add_item(i=i, pt=pts[1])
        self.__sol = ans
        return ans

    def get_solution(self):
        return self.__sol
            
    def can_fit_in(self, i):
        return bool(i.get_weight() <= self.__weight and
            i.get_width() <= self.__width and
            i.get_height() <= self.__height and
            i.get_depth() <= self.__depth)
    



        




