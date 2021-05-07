import { RaffleNumbers } from "./raffle-numbers";

export class RaffleValues {
    id:number;
    name: string;
    date: Date;
    percentage: number;
    image: FormData;
    raffleNumbers: RaffleNumbers[];
    description: string;
    selledPercentage:number;
    freePercentage:number;
    selectedPercentage:number;
    retreivedImage:any;
    status:string;
}
