export class Comment {
  id: number;
  parent: {
    id: number
  };
  children: Comment[];
  authorId: number;
  addDate: Date;
  articleId: number;
  content: string;

  constructor() { }
}
