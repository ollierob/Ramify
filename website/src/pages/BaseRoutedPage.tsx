import * as React from "react";
import {RouteComponentProps} from "react-router";
import BasePage from "./BasePage";

export type BaseRoutedPageProps = RouteComponentProps<any>;

export default abstract class BaseRoutedPage<S = any> extends BasePage<BaseRoutedPageProps, S> {

    private readonly onHashChange = () => this.hashChanged();

    urlParameter(key: string): string {
        const location = this.props.location;
        if (!location) return null;
        return new URLSearchParams(location.search).get(key);
    }

    componentDidMount() {
        this.setPageTitle();
        window.addEventListener("hashchange", this.onHashChange, false);
    }

    componentWillUnmount() {
        window.removeEventListener("hashchange", this.onHashChange, false);
    }

    protected hashChanged(): void {
    }

}

