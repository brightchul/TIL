import { consoleLog } from "./console";
import { editDistance as editDistanceRecursive } from "./minimumCostRecursive";
import { editDistance as editDistanceDP } from "./minimumCostDP";

consoleLog("sunday", "saturday", editDistanceRecursive("sunday", "saturday"));
consoleLog("sunday", "saturday", editDistanceDP("sunday", "saturday"));
