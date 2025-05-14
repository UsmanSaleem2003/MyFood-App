from pydantic import BaseModel
from typing import List, Optional

class ReviewOut(BaseModel):
    id: int
    text: str
    class Config:
        orm_mode = True

class ReviewCreate(BaseModel):
    text: str

class ProductOut(BaseModel):
    id: int
    name: str
    category: str
    image_name: str
    price: float
    rating: float
    timing: str
    description: Optional[str]
    ingredients: Optional[str]
    reviews: List[ReviewOut] = []

    class Config:
        orm_mode = True
