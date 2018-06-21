import {Rank} from "./rank";

export class Profile {
  userName: string;
  firstName: string;
  lastName: string;
  rating: number;
  avatarUrl: string;
  about: string;
  lastOnline: Date;
  regDate: Date;
  rank: Rank;
  country: string;
  city: string;
  birthDate: Date;
  gender: string;
}
