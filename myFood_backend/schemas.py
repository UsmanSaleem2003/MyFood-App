from pydantic import BaseModel

class ProductOut(BaseModel):
    name: str
    category: str
    image_name: str
    price: float
    rating: float
    timing: str

    class Config:
        orm_mode = True
