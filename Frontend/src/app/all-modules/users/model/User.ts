export class User {

  id?:string;
  name:string;
  username?:string;
  password?:string;
  email?:string;

  userTitle?:string;
  groupUser?:boolean;
  groupUsername?:string;

  // System log field
  creationdateTime?:Date;
  creationUser?:string;
  lastUpdateDateTime?:Date;
  lastUpdateUser?:string;


}
