import BasePage from "../BasePage";
import {HeaderMenuType} from "../HeaderMenu";

export abstract class RecordBasePage<P, S> extends BasePage<P, S> {
    
    active(): HeaderMenuType {
        return "records";
    }

}