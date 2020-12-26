import { consoleLog } from "./console";
import { editDistanceRecursive, editDistanceDP } from "./minimumCost";
import {
  numOfPathsRecursive,
  numOfPathsDP,
  numOfPathsDP2,
} from "./rectangleTotalPath";

consoleLog("sunday", "saturday", editDistanceRecursive("sunday", "saturday"));
consoleLog("sunday", "saturday", editDistanceDP("sunday", "saturday"));

consoleLog("numOfPathsRecursive", numOfPathsRecursive(3, 4));
consoleLog("numOfPathsDP", numOfPathsDP(3, 4));
consoleLog("numOfPathsDP2", numOfPathsDP2(3, 4));
