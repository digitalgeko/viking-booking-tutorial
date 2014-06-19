<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${the_title} - ${company_name}</title>

    <meta content="initial-scale=1.0, width=device-width" name="viewport" />

	${theme.include(top_head_include)}
	<link href='//fonts.googleapis.com/css?family=Ubuntu:400,400italic,700' rel='stylesheet' type='text/css'>
	<link href='//fonts.googleapis.com/css?family=Oxygen:400,700' rel='stylesheet' type='text/css'>
</head>

<body class="${css_class}">

<a href="#main-content" id="skip-to-content"><@liferay.language key="skip-to-content" /></a>

${theme.include(body_top_include)}

<#if is_signed_in>
	<@liferay.dockbar />
</#if>

<div class="container-fluid" id="wrapper">
	<header id="banner" role="banner">
		<div id="heading">
			<h1 class="site-title">
				<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language key="go-to" /> ${site_name}">
					<img alt="${logo_description}" height="${site_logo_height}" src="${site_logo}" width="${site_logo_width}" />

				<#if show_site_name>
					<span class="site-name" title="<@liferay.language key="go-to" /> ${site_name}">
						${site_name}
					</span>
				</#if>
                </a>

                <#assign create_account_url = portletURLFactory.create(request, "58", page.getPlid(), "RENDER_PHASE")>
				${create_account_url.setWindowState("normal")}
				${create_account_url.setPortletMode("view")}
				${create_account_url.setParameter("saveLastPath", "0")}
				${create_account_url.setParameter("struts_action", "/login/create_account")}

				<div class="headerButton">
	                <#if paramUtil.getString(request, "_58_struts_action") != "/login/create_account">

						<label><@liferay.language key="not.a.member.yet" /></label>
						<span>
							<a href="${create_account_url}">
								<@liferay.language key="sign.up" />
							</a>
						</span>
					<#else>
						<label><@liferay.language key="already.a.member" /></label>
						<span>
							<a href="${themeDisplay.getPortalURL()}">
								<@liferay.language key="log.in" />
							</a>
						</span>
	                </#if>
                </div>

				
			</h1>

			<h2 class="page-title">
				<span>${the_title}</span>
			</h2>
		</div>
		
		
	</header>

	<div id="content">
        <#if theme.getSetting("show-navigation") = "true">
		<#if has_navigation || is_signed_in>
			<#include "${full_templates_path}/navigation.ftl" />
		</#if>
        </#if>


		<#if selectable>
			${theme.include(content_include)}
		<#else>
			${portletDisplay.recycle()}

			${portletDisplay.setTitle(the_title)}

			${theme.wrapPortlet("portlet.ftl", content_include)}
		</#if>
	</div>

	<footer id="footer" role="contentinfo">
		<p>
		</p>
	</footer>
</div>

${theme.include(body_bottom_include)}

${theme.include(bottom_include)}

</body>

</html>