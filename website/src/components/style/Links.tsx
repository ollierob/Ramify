import * as React from "react";
import {Link as LinkProto} from "../../protobuf/generated/link_pb";
import {Icon, Tag} from "antd";
import TypeMapValues = LinkProto.Type;

export const LinkTags = (props: {links: ReadonlyArray<LinkProto.AsObject>}) => {
    const links = props.links;
    if (!links || !links.length) return null;
    return <div className="links">
        {links.map(link => <Tag><Link link={link} newWindow>{link.text}</Link></Tag>)}
    </div>;
};

export const Link = (props: {link: LinkProto.AsObject, iconPath?: string, children?: React.ReactNode, newWindow?: boolean}) => {
    const link = props.link;
    if (!link) return null;
    const icon = props.iconPath ? <img src={props.iconPath} className="image"/> : linkIcon(link);
    return <a href={link.href} target={props.newWindow ? "_blank" : "_self"} className="link">
        {icon}
        {props.children && <> {props.children}</>}
    </a>;
};

function linkIcon(link: LinkProto.AsObject): React.ReactNode {
    switch (link.type) {
        case TypeMapValues.WIKIPEDIA:
            return <img src="/images/links/wikipedia.png" className="image" alt="Wikipedia" title="Wikipedia link"/>;
        case TypeMapValues.NATIONAL_ARCHIVES:
            return <img src="/images/links/national_archives.png" className="image" alt="National Archives" title="National Archives link"/>;
        default:
            return null;
    }
}