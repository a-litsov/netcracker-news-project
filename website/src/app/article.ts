import { Category } from './category';
import { Tag } from './tag';

export class Article {
  id: number;
  category: Category;
  tag: Tag;
  title: string;
  logoSrc: string;
  content: string;
  authorId: number;
  addDate: Date;
}
