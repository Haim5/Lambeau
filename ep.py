from item import Item
import math
class EP:
    def __init__(self, x, y, z, rs):
        self.__x = x
        self.__y = y
        self.__z = z
        self.__rs = rs

    def get_x(self):
        return self.__x

    def get_y(self):
        return self.__y
    
    def get_z(self):
        return self.__z

    def overlap_check(self, i):
        return bool(i.get_width() <= self.__rs['X'] and i.get_depth() <= self.__rs['Y'] and i.get_height() <= self.__rs['Z'])
    
    def get_rs(self, key):
        return self.__rs[key]
    
    def update_rs(self, k):
        if self.__z >= k.get_z() and self.__z < k.get_z() + k.get_height():
            if self.__x <= k.get_x() and k.get_y() <= self.__y <= k.get_y() + k.get_depth():
                self.__rs['X'] = math.min(self.__rs['X'], k.get_x() - self.__x)
            if self.__y <= k.get_y() and k.get_x() <= self.__x <= k.get_x() + k.get_width():
                self.__rs['Y'] = math.min(self.__rs['Y'], k.get_y() - self.__y)
        if (self.__z <= k.get_z() and
             k.get_y() <= self.__y <= k.get_y() + k.get_depth() and
             k.get_x() <= self.__x <= k.get_x() + k.get_width()):
            self.__rs['Z'] = math.min(self.__rs['Z'], k.get_z() - self.__z)

