"use strict";(self.webpackChunkhertzbeat=self.webpackChunkhertzbeat||[]).push([[75196],{15680:(e,t,r)=>{r.d(t,{xA:()=>s,yg:()=>y});var n=r(96540);function a(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function o(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function l(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?o(Object(r),!0).forEach((function(t){a(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):o(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function i(e,t){if(null==e)return{};var r,n,a=function(e,t){if(null==e)return{};var r,n,a={},o=Object.keys(e);for(n=0;n<o.length;n++)r=o[n],t.indexOf(r)>=0||(a[r]=e[r]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(n=0;n<o.length;n++)r=o[n],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(a[r]=e[r])}return a}var c=n.createContext({}),p=function(e){var t=n.useContext(c),r=t;return e&&(r="function"==typeof e?e(t):l(l({},t),e)),r},s=function(e){var t=p(e.components);return n.createElement(c.Provider,{value:t},e.children)},d={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},u=n.forwardRef((function(e,t){var r=e.components,a=e.mdxType,o=e.originalType,c=e.parentName,s=i(e,["components","mdxType","originalType","parentName"]),u=p(r),y=a,g=u["".concat(c,".").concat(y)]||u[y]||d[y]||o;return r?n.createElement(g,l(l({ref:t},s),{},{components:r})):n.createElement(g,l({ref:t},s))}));function y(e,t){var r=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var o=r.length,l=new Array(o);l[0]=u;var i={};for(var c in t)hasOwnProperty.call(t,c)&&(i[c]=t[c]);i.originalType=e,i.mdxType="string"==typeof e?e:a,l[1]=i;for(var p=2;p<o;p++)l[p]=r[p];return n.createElement.apply(null,l)}return n.createElement.apply(null,r)}u.displayName="MDXCreateElement"},77330:(e,t,r)=>{r.r(t),r.d(t,{assets:()=>c,contentTitle:()=>l,default:()=>d,frontMatter:()=>o,metadata:()=>i,toc:()=>p});var n=r(58168),a=(r(96540),r(15680));const o={id:"baota-deploy",title:"\u4f7f\u7528 \u5b9d\u5854\u9762\u677f \u90e8\u7f72 HertzBeat",sidebar_label:"\u57fa\u4e8e\u5b9d\u5854\u9762\u677f\u90e8\u7f72"},l=void 0,i={unversionedId:"start/baota-deploy",id:"start/baota-deploy",title:"\u4f7f\u7528 \u5b9d\u5854\u9762\u677f \u90e8\u7f72 HertzBeat",description:"Apache HertzBeat (incubating) \u652f\u6301\u5728\u5b9d\u5854\u9762\u677f\u7684 Docker \u5e94\u7528\u5546\u5e97\u4e00\u952e\u90e8\u7f72\u3002",source:"@site/i18n/zh-cn/docusaurus-plugin-content-docs/current/start/baota-deploy.md",sourceDirName:"start",slug:"/start/baota-deploy",permalink:"/zh-cn/docs/start/baota-deploy",draft:!1,editUrl:"https://github.com/apache/hertzbeat/edit/master/home/i18n/zh-cn/docusaurus-plugin-content-docs/current/start/baota-deploy.md",tags:[],version:"current",frontMatter:{id:"baota-deploy",title:"\u4f7f\u7528 \u5b9d\u5854\u9762\u677f \u90e8\u7f72 HertzBeat",sidebar_label:"\u57fa\u4e8e\u5b9d\u5854\u9762\u677f\u90e8\u7f72"},sidebar:"docs",previous:{title:"\u5b89\u88c5\u5305\u65b9\u5f0f\u5b89\u88c5",permalink:"/zh-cn/docs/start/package-deploy"},next:{title:"\u57fa\u4e8eRainbond\u90e8\u7f72",permalink:"/zh-cn/docs/start/rainbond-deploy"}},c={},p=[{value:"\u524d\u63d0",id:"\u524d\u63d0",level:2},{value:"\u5b89\u88c5 Docker",id:"\u5b89\u88c5-docker",level:2},{value:"\u90e8\u7f72 HertzBeat",id:"\u90e8\u7f72-hertzbeat",level:2},{value:"\u8bbf\u95ee HertzBeat",id:"\u8bbf\u95ee-hertzbeat",level:2}],s={toc:p};function d(e){let{components:t,...o}=e;return(0,a.yg)("wrapper",(0,n.A)({},s,o,{components:t,mdxType:"MDXLayout"}),(0,a.yg)("p",null,"Apache HertzBeat (incubating) \u652f\u6301\u5728\u5b9d\u5854\u9762\u677f\u7684 ",(0,a.yg)("inlineCode",{parentName:"p"},"Docker")," \u5e94\u7528\u5546\u5e97\u4e00\u952e\u90e8\u7f72\u3002"),(0,a.yg)("h2",{id:"\u524d\u63d0"},"\u524d\u63d0"),(0,a.yg)("p",null,"\u5b89\u88c5\u5b9d\u5854\u9762\u677f\uff0c\u8bf7\u53c2\u9605 ",(0,a.yg)("a",{parentName:"p",href:"https://www.bt.cn/new/download.html"},"\u5b9d\u5854\u9762\u677f\u5feb\u901f\u5b89\u88c5"),"\u3002"),(0,a.yg)("h2",{id:"\u5b89\u88c5-docker"},"\u5b89\u88c5 Docker"),(0,a.yg)("blockquote",null,(0,a.yg)("p",{parentName:"blockquote"},"\u5728\u5b9d\u5854\u9762\u677f\u5b89\u88c5 Docker \u670d\u52a1\uff0c\u82e5\u5df2\u6709\u5219\u8df3\u8fc7\u3002")),(0,a.yg)("p",null,"\u767b\u5f55\u5b9d\u5854\u9762\u677f\uff0c\u70b9\u51fb\u5de6\u4fa7\u83dc\u5355\u4e2d\u7684 ",(0,a.yg)("inlineCode",{parentName:"p"},"Docker"),"\uff0c\u6839\u636e\u63d0\u793a\u5b89\u88c5 ",(0,a.yg)("inlineCode",{parentName:"p"},"Docker")," \u548c ",(0,a.yg)("inlineCode",{parentName:"p"},"Docker Compose")," \u670d\u52a1\u3002"),(0,a.yg)("h2",{id:"\u90e8\u7f72-hertzbeat"},"\u90e8\u7f72 HertzBeat"),(0,a.yg)("p",null,"\u767b\u5f55\u5b9d\u5854\u9762\u677f\uff0c\u70b9\u51fb\u5de6\u4fa7\u83dc\u5355 ",(0,a.yg)("inlineCode",{parentName:"p"},"Docker")," \u91cc\u9762\u7684 ",(0,a.yg)("inlineCode",{parentName:"p"},"\u5e94\u7528\u5546\u5e97"),"\uff0c\u5728\u641c\u7d22\u6846\u4e2d\u641c\u7d22 ",(0,a.yg)("inlineCode",{parentName:"p"},"HertzBeat"),"\uff0c\u5373\u53ef\u5feb\u901f\u5b89\u88c5\u3002"),(0,a.yg)("p",null,(0,a.yg)("img",{alt:"HertzBeat",src:r(31964).A,width:"1432",height:"794"})),(0,a.yg)("p",null,"\u70b9\u51fb ",(0,a.yg)("inlineCode",{parentName:"p"},"\u5b89\u88c5"),"\uff0c\u6839\u636e\u63d0\u793a\u914d\u7f6e\u540d\u79f0\uff0c\u7248\u672c\uff0c\u8bbf\u95ee\u7b49\uff0c\u7b49\u5f85\u5b89\u88c5\u5b8c\u6210\u3002"),(0,a.yg)("p",null,(0,a.yg)("img",{alt:"HertzBeat",src:r(73719).A,width:"1411",height:"488"})),(0,a.yg)("h2",{id:"\u8bbf\u95ee-hertzbeat"},"\u8bbf\u95ee HertzBeat"),(0,a.yg)("p",null,"\u6d4f\u89c8\u5668\u8bbf\u95ee ",(0,a.yg)("inlineCode",{parentName:"p"},"http://<\u5b9d\u5854\u9762\u677fIP>:1157"),"\uff0c\u5373\u53ef\u8bbf\u95ee HertzBeat \u63a7\u5236\u53f0\u3002"))}d.isMDXComponent=!0},31964:(e,t,r)=>{r.d(t,{A:()=>n});const n=r.p+"assets/images/install-to-baota-1-486e8c515fac02152fe91e6d16bad605.png"},73719:(e,t,r)=>{r.d(t,{A:()=>n});const n=r.p+"assets/images/install-to-baota-2-f896084de81057e6e1f7c2f4e639bfef.png"}}]);