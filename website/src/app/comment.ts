export class Comment {
  id: number;
  parent: {
    id: number
  };
  children: Comment[];
  authorName: string;
  addDate: Date;
  articleId: number;
  content: string;

  constructor() { }
}
