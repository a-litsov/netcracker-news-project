import { Category } from '../article/category';
import { Tag } from './tag';

export class Preview {
  id: number;
  category: Category;
  tag: Tag;
  title: string;
  logoSrc: string;
}
